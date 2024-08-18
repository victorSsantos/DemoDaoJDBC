package model.dao;

import db.DB;
import model.dao.implementations.DepartmentDaoJDBC;
import model.dao.implementations.SellerDaoJDBC;
import model.dao.interfaces.DepartmentDao;
import model.dao.interfaces.SellerDao;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }

    public static void closeConnection(){
        DB.closeConnection();
    }
}
