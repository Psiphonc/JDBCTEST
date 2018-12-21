package JDBCTEST;

import java.util.HashMap;
import java.util.Objects;

public abstract class RepoItem {

    HashMap<String, String> attributes;
    String pk;

    RepoItem() {
        attributes = new HashMap<>();
        pk = getPrimaryKey();
    }

    RepoItem(String k) {
        this();
        attributes.put(pk, k);
    }

    abstract String getPrimaryKey();

    abstract void updateAttributes();

    abstract public void changeCommitted();

    String getUpdateStatement() {
        String ret = "";
        for (String k :
                attributes.keySet()) {
            ret += k;
            ret += " = ";
            ret += attributes.get(k);
            ret += ", ";
        }
        ret = ret.substring(0, ret.length() - 2);
        ret = ret + " WHERE " + pk + " = " + attributes.get(pk);
        return ret;
    }

    String getInsertStatement() {
        String ret1 = " VALUES(";
        String ret0 = "(";
        for (String k :
                attributes.keySet()) {
            ret0 += k;
            ret0 += ",";
            ret1 += attributes.get(k);
            ret1 += ",";
        }
        ret0 = ret0.substring(0, ret0.length() - 1);
        ret0 += ")";
        ret1 = ret1.substring(0, ret1.length() - 1);
        ret1 += ")";
        return ret0 + ret1;
    }

    abstract boolean isChanged();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepoItem ri = (RepoItem) o;
        return this.attributes.get(pk).equals(ri.attributes.get(pk));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.attributes.get(pk));
    }


}
