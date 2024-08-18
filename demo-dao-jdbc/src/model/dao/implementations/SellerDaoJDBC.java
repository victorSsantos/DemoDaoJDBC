package model.dao.implementations;

import db.DB;
import db.DbException;
import model.dao.interfaces.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;
    private final String findAllQuery =
            "SELECT seller.* , department.Name as DepName " +
            "FROM seller " +
            "LEFT JOIN department ON seller.DepartmentId = department.Id";

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
    public Seller findById(Integer id) {
        Seller seller = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try{
            statement = connection.prepareStatement(findAllQuery + " WHERE seller.Id = ?");
            statement.setInt(1,id);
            result = statement.executeQuery();

            if(result.next())
                seller = new Seller(
                        result.getInt("Id"),
                        result.getString("Name"),
                        result.getString("Email"),
                        result.getDate("BirthDate"),
                        result.getDouble("BaseSalary"),
                        new Department(result.getInt("DepartmentId"), result.getString("DepName")));
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }
        return seller;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Seller> sellers = new ArrayList<>();

        try{
            statement = connection.prepareStatement(findAllQuery);
            result = statement.executeQuery();

            while(result.next()) {
                 var seller = new Seller(result.getInt(1)
                        , result.getString(2)
                        , result.getString(3)
                        , result.getDate(4)
                        , result.getDouble(5)
                        , new Department(result.getInt(6),result.getString(7)));

                 sellers.add(seller);
            }
            return sellers;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }
    }
}
