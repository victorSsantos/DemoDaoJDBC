package model.dao.implementations;

import db.DB;
import db.DbException;
import model.dao.interfaces.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;
    private final String findAllQuery = "SELECT * FROM department ";

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
        Statement statement = null;
        ResultSet result = null;

        try{
            statement = connection.createStatement();
            result = statement.executeQuery(findAllQuery + "WHERE Id = " + id);

            if(result.next())
                department = new Department(result.getInt(1), result.getString(2));
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
        Statement statement = null;
        ResultSet result = null;

        try{
            statement = connection.createStatement();
            result = statement.executeQuery(findAllQuery);

            while(result.next()) {
                var department = new Department(result.getInt(1), result.getString(2));
                departments.add(department);
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
}
