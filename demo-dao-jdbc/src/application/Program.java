package application;

import model.dao.DaoFactory;
import model.dao.interfaces.DepartmentDao;
import model.dao.interfaces.SellerDao;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println();
        System.out.println("############# SELLERS ################");
        System.out.println("Find By ID : ");
        System.out.println(sellerDao.findById(3));
        System.out.println("Find All :");
        sellerDao.findAll().forEach(System.out::println);

        System.out.println();
        System.out.println("############# DEPARTMENTS ############");
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("Find By ID :");
        System.out.println(departmentDao.findById(3));
        System.out.println("Find All :");
        departmentDao.findAll().forEach(System.out::println);

        System.out.println();
        DaoFactory.closeConnection();
    }
}
