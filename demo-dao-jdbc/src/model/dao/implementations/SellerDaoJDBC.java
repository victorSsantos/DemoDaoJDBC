package model.dao.implementations;

import db.DB;
import db.DbException;
import model.dao.interfaces.SellerDao;
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

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Seller> findAll(){
        return find(null);
    }

    @Override
    public Seller findById(Integer id) {
        return find(" WHERE seller.Id = " + id).getFirst();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        return find(" WHERE seller.DepartmentId = " + department.getId());
    }

    private List<Seller> find(String whereClause) {
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            String findAllQuery =
                    "SELECT seller.* , department.Name as DepName FROM seller " +
                    "LEFT JOIN department ON seller.DepartmentId = department.Id";

            statement = connection.prepareStatement(whereClause!=null ? findAllQuery+whereClause : findAllQuery);
            result = statement.executeQuery();
            Map<Integer, Department> mapDepartment = new HashMap<>();

            while(result.next()) {
                Department department = mapDepartment.get(result.getInt("DepartmentId"));

                if(department==null){
                    department = new Department(result.getInt("DepartmentId"), result.getString("DepName"));
                    mapDepartment.put(result.getInt("DepartmentId"), department);
                }
                sellers.add(databaseToSeller(result, department));
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

    private Seller databaseToSeller(ResultSet result, Department department ) throws SQLException {
        return new Seller(
                result.getInt("Id"),
                result.getString("Name"),
                result.getString("Email"),
                result.getDate("BirthDate"),
                result.getDouble("BaseSalary"),
                department);
    }
}
