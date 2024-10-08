package dao.impl;

import config.db.DB;
import config.db.exceptions.DbException;
import dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;
    private final String findAllQuery = "SELECT * FROM department";

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        Department department = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            statement = connection.prepareStatement(findAllQuery + " WHERE Id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();
            if(result.next()) department = databaseToDepartment(result);
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            statement = connection.prepareStatement(findAllQuery);
            result = statement.executeQuery();

            while(result.next()) {
                departments.add(databaseToDepartment(result));
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }
        return departments;
    }

    private Department databaseToDepartment(ResultSet result) throws SQLException {
        return new Department(result.getInt("Id"), result.getString("Name"));
    }
}
