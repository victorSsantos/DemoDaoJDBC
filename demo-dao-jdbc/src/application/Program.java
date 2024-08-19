package application;

import dao.factory.DaoFactory;
import dao.SellerDao;
import model.entities.Department;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println();
        System.out.println("=== TEST 1 - Find By ID === ");
        System.out.println(sellerDao.findById(3));

        System.out.println();
        System.out.println("=== TEST 2 - Find By Department === ");
        sellerDao.findByDepartment(new Department(4,null)).forEach(System.out::println);

        System.out.println();
        System.out.println("=== TEST 3 - Find All === ");
        sellerDao.findAll().forEach(System.out::println);

        System.out.println();
        DaoFactory.closeConnection();
    }
}
