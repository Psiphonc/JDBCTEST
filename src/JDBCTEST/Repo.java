package JDBCTEST;

import java.sql.*;
import java.util.ArrayList;

public abstract class Repo<T> {
    private Connection connection;
    private Statement statement;

    abstract ArrayList<RepoItem> getEntitySet();

    abstract String[] getAttribute();

    abstract String getPrimaryKey();

    abstract String getURL();

    abstract String getName();

    abstract String getPassWord();

    abstract String getDBMSType();

    abstract String getSchemaName();

    abstract ArrayList<T> getRemoveRecord();

    abstract ArrayList<RepoItem> getAddRecord();

    public void connect() {
        try {
            Class.forName(getDBMSType());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(getURL(), getName(), getPassWord());
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet getResultSet() {
        ResultSet ret = null;
        try {
            ret = statement.executeQuery("SELECT  * FROM " + getSchemaName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    ResultSet getResultSet(String primaryKey) {
        ResultSet ret = null;
        try {
            ret = statement.executeQuery("SELECT  * FROM " + getSchemaName() +
                    " WHERE " + getPrimaryKey() + " = " + primaryKey
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    void commitChanges() {
        ArrayList<RepoItem> entitySet = getEntitySet();
        ArrayList<RepoItem> addRecord = getAddRecord();
        ArrayList<T> removeRecord = getRemoveRecord();
        if (!removeRecord.isEmpty()) {
            for (int i = 0; i < removeRecord.size(); ++i) {
                try {
                    statement.execute(
                            "DELETE FROM " + getSchemaName()
                                    + " WHERE " + getPrimaryKey()
                                    + " = " + "\'" + removeRecord.get(i).toString() + "\'"
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                removeRecord.clear();
            }
        }
        if (!addRecord.isEmpty()) {
            for (int i = 0; i < addRecord.size(); ++i) {
                try {
                    statement.execute(
                            "INSERT INTO " + getSchemaName()
                                    + addRecord.get(i).getInsertStatement()
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addRecord.clear();
            }
            for (RepoItem entity :
                    entitySet) {
                if (entity.isChanged()) {
                    String[] attributes = getAttribute();
                    try {
                        statement.execute(
                                "UPDATE " + getSchemaName()
                                        + " SET " + entity.getUpdateStatement()
                        );
                        entity.changeCommitted();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
