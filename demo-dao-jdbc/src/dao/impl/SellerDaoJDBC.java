package dao.impl;

import config.db.DB;
import config.db.exceptions.DbException;
import dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;

        try{

            statement = connection.prepareStatement(
                    "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            var rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                ResultSet result = statement.getGeneratedKeys();
                if(result.next())
                    obj.setId(result.getInt(1));
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(
                    "UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ? ");

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());
            statement.setInt(6, obj.getId());

            System.out.println(statement.executeUpdate() + " row(s) affected!");
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement("DELETE FROM seller WHERE iD = ?");
            statement.setInt(1, id);
            System.out.println(statement.executeUpdate() + " row(s) affected!");
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Seller> findAll(){
        return find(null,null);
    }

    @Override
    public Seller findById(Integer id) {
        return find("seller.Id",id).getFirst();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        return find("seller.DepartmentId",department.getId());
    }

    private List<Seller> find(String key, Integer value) {
        List<Seller> sellers = new ArrayList<>();
        Statement statement = null;
        ResultSet result = null;
        String whereClause = null;

        try{
            String query =
                    "SELECT seller.*, department.Name as DepName FROM seller " +
                    "LEFT JOIN department ON seller.DepartmentId = department.Id";

            if(key!=null && value!=null)
                whereClause = " WHERE " + key + " = " + value;

            statement = connection.createStatement();
            result = statement.executeQuery(whereClause!=null ? query+whereClause : query);
            Map<Integer, Department> mapDepartment = new HashMap<>();

            while(result.next()) {
                Department department = mapDepartment.get(result.getInt("DepartmentId"));

                if(department==null){
                    department = new Department(result.getInt("DepartmentId"), result.getString("DepName"));
                    mapDepartment.put(result.getInt("DepartmentId"), department);
                }

                sellers.add(
                        new Seller(
                        result.getInt("Id"),
                        result.getString("Name"),
                        result.getString("Email"),
                        result.getDate("BirthDate"),
                        result.getDouble("BaseSalary"),
                        department));
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }
        return sellers;
    }
}
