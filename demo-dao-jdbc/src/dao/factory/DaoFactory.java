package dao.factory;

import config.db.DB;
import dao.DepartmentDao;
import dao.SellerDao;
import dao.impl.DepartmentDaoJDBC;
import dao.impl.SellerDaoJDBC;

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
