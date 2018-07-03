package com.kanodoe.talk.kitchen.employee.dao.query;

import static com.kanodoe.talk.kitchen.employee.util.Constant.DB_SCHEMA;

public class QueryEmployeeUtil {

    private QueryEmployeeUtil() {
        throw new IllegalAccessError(QueryEmployeeUtil.class.toString());
    }

    private static class QSELECT {

        private StringBuilder select = new StringBuilder();
        private StringBuilder postSelect = new StringBuilder();

        private StringBuilder getSelect() {

            select.append("  SELECT ");
            select.append("  employee.id, ");
            select.append("  employee.first_name,  ");
            select.append("  employee.second_name,  ");
            select.append("  employee.last_name,  ");
            select.append("  employee.second_last_name,  ");
            select.append("  employee.email,  ");
            select.append("  employee.gender,  ");
            select.append("  employee.phone_country,  ");
            select.append("  employee.phone_region,  ");
            select.append("  employee.phone_number,  ");
            select.append("  employee.country,  ");
            select.append("  employee.state,  ");
            select.append("  employee.city,  ");
            select.append("  employee.address,  ");
            select.append("  employee.entry_date,  ");
            select.append("  employee.category,  ");
            select.append("  employee.level,  ");
            select.append("  employee.is_active  ");
            select.append("  FROM ");
            select.append(DB_SCHEMA);
            select.append(".kt_employees employee ");
            return select;
        }

        private QSELECT postSelect(StringBuilder where) {
            this.postSelect.append(" ").append(where);

            return this;
        }

        @Override
        public String toString() {
            return this.getSelect().append(this.postSelect).toString();
        }
    }

    public static String getEmployeesSql () {
        return new QSELECT().toString();
    }

    public static String getEmployeeByIdSql () {
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        where.append(" employee.id = ? ");
        return new QSELECT().postSelect(where).toString();
    }

    public static String postEmployeeSql () {
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(DB_SCHEMA);
        query.append(".kt_employees ");
        query.append("( ");
        query.append(" first_name, ");
        query.append(" second_name, ");
        query.append(" last_name, ");
        query.append(" second_last_name, ");
        query.append(" email, ");
        query.append(" gender, ");
        query.append(" phone_country, ");
        query.append(" phone_region, ");
        query.append(" phone_number, ");
        query.append(" country, ");
        query.append(" state, ");
        query.append(" city, ");
        query.append(" address, ");
        query.append(" category, ");
        query.append(" level, ");
        query.append(" entry_date");
        query.append(") ");
        query.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

        return query.toString();
    }

    public static String putEmployeeSql () {
        StringBuilder query = new StringBuilder();

        query.append("UPDATE ");
        query.append(DB_SCHEMA);
        query.append(".kt_employees SET ");
        query.append(" first_name = ?, ");
        query.append(" second_name = ?, ");
        query.append(" last_name = ?, ");
        query.append(" second_last_name = ?, ");
        query.append(" email = ?, ");
        query.append(" gender = ?, ");
        query.append(" phone_country = ?, ");
        query.append(" phone_region = ?, ");
        query.append(" phone_number = ?, ");
        query.append(" country = ?, ");
        query.append(" state = ?, ");
        query.append(" city = ?, ");
        query.append(" address = ?, ");
        query.append(" category = ?, ");
        query.append(" level = ? ");
        query.append(" WHERE id = ? ");

        return query.toString();
    }

    public static String deleteEmployeeSql() {
        StringBuilder query = new StringBuilder();

        query.append("UPDATE ");
        query.append(DB_SCHEMA);
        query.append(".kt_employees SET ");
        query.append(" is_active = 'N' ");
        query.append(" WHERE id = ? ");

        return query.toString();
    }

    public static String getByPageSql() {
        StringBuilder limit = new StringBuilder();
        limit.append(" LIMIT ? OFFSET ? ");
        return new QSELECT().postSelect(limit).toString();
    }

    public static String getEmployeesCountSql() {
        StringBuilder count = new StringBuilder();
        count.append(" SELECT count(1) ");
        count.append(" FROM ");
        count.append(DB_SCHEMA);
        count.append(".kt_employees employees ");
        count.append(" WHERE ");
        count.append(" employees.is_active = ? ");

        return count.toString();
    }
}
