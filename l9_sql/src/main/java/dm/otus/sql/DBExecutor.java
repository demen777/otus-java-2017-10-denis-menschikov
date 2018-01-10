package dm.otus.sql;

import dm.otus.sql.base.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DBExecutor implements Executor {
    private final Connection connection;

    DBExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void createDBObjects(Class<T> clazz) throws SQLException {
        createSequence(clazz);
        createTable(clazz);
    }

    private <T extends DataSet> void createTable(Class<T> clazz) throws SQLException {
        final String CREATE_TABLE = "CREATE TABLE %s (id bigint, %s)";
        StringJoiner fields_joiner = new StringJoiner(",");
        List<String> fieldNames = getFieldNames(clazz);
        for(String fieldName:fieldNames) {
            try {
                fields_joiner.add(String.format("%s %s", fieldName,
                        mapToDBType(clazz.getDeclaredField(fieldName).getType())));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        String sql = String.format(CREATE_TABLE, getTableName(clazz), fields_joiner.toString());
        Statement statement = connection.createStatement();
        statement.execute(String.format(sql, getTableName(clazz)));
    }

    private Object mapToDBType(Class<?> type) {
        switch (type.getCanonicalName()) {
            case "long":
                return "bigint";
            case "int":
                return "integer";
            case "java.lang.String":
                return "text";
            default:
                throw new NotDefinedMappingException(String.format("Type %s is not mapped to DB type",
                        type.getCanonicalName()));
        }
    }

    private <T extends DataSet> void createSequence(Class<T> clazz) throws SQLException {
        final String CREATE_SEQUENCE = "CREATE SEQUENCE %s_seq";
        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_SEQUENCE, getTableName(clazz)));
    }

    private <T extends DataSet> String getTableName(Class<T> clazz) {
        String res = clazz.getSimpleName();
        return res.substring(0, res.length()-"DataSet".length()) + "s";
    }

    private <T extends DataSet> List<String> getFieldNames(Class<T> clazz) {
        List<String> fieldNames = new ArrayList<>();
        for(Field field:clazz.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        user.setId(getId(user.getClass()));
        final String INSERT_INTO = "INSERT INTO %s (id, %s) values(?, %s)";
        StringJoiner fields_joiner = new StringJoiner(",");
        StringJoiner params_joiner = new StringJoiner(",");
        List<String> fieldNames = getFieldNames(user.getClass());
        for(String fieldName:fieldNames) {
            fields_joiner.add(fieldName);
            params_joiner.add("?");
        }
        final String insertSql = String.format(INSERT_INTO, getTableName(user.getClass()),
                fields_joiner.toString(), params_joiner.toString());
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setLong(1, user.getId());
        int counter = 2;
        for(String fieldName:fieldNames) {
            Object value = ReflectionHelper.getFieldValue(user, fieldName);
            insertStatement.setObject(counter++, value);
        }
        insertStatement.execute();
    }

    private <T extends DataSet> long getId(Class<T> clazz) throws SQLException {
        final String SELECT_SEQUENCE = "SELECT nextval('%s_seq')";
        Statement statement = connection.createStatement();
        String sql = String.format(SELECT_SEQUENCE, getTableName(clazz));
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getLong(1);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        final String SELECT_FROM = "SELECT %s FROM %s WHERE id=%d";
        StringJoiner fields_joiner = new StringJoiner(",");
        List<String> fieldNames = getFieldNames(clazz);
        for(String fieldName:fieldNames) {
            fields_joiner.add(fieldName);
        }
        final String selectSql = String.format(SELECT_FROM, fields_joiner.toString(), getTableName(clazz), id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSql);
        if (resultSet.next()) {
            try {
                T res = clazz.getDeclaredConstructor().newInstance();
                for(String fieldName:fieldNames) {
                    Object value = resultSet.getObject(fieldName);
                    ReflectionHelper.setFieldValue(res, fieldName, value);
                }
                res.setId(id);
                return res;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T extends DataSet> void clearAll(Class<T> clazz) throws SQLException {
        final String DELETE_ALL = "DELETE FROM %s";
        final String deleteSql = String.format(DELETE_ALL, getTableName(clazz));
        Statement deleteStatement = connection.createStatement();
        deleteStatement.execute(deleteSql);
    }

    private class NotDefinedMappingException extends RuntimeException {
        NotDefinedMappingException(String format) {
            super(format);
        }
    }
}
