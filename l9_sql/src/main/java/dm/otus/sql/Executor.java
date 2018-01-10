package dm.otus.sql;

import dm.otus.sql.base.DataSet;

import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public interface Executor {
    <T extends DataSet> void save(T user) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
