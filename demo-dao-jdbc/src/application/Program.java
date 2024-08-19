package application;

import dao.DepartmentDao;
import dao.factory.DaoFactory;
import dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program {

    public static void main(String[] args)   {

        Seller seller;
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        SellerDao sellerDao = DaoFactory.createSellerDao();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println();
        System.out.println("=== TEST 1 - Find By ID === ");
        System.out.println(sellerDao.findById(3));

        System.out.println();
        System.out.println("=== TEST 2 - Find By Department === ");
        sellerDao.findByDepartment(new Department(4,null)).forEach(System.out::println);

        System.out.println();
        System.out.println("=== TEST 3 - Find All === ");
        sellerDao.findAll().forEach(System.out::println);

        try{
            System.out.println();
            System.out.println("=== TEST 4 - Insert === ");
            seller = new Seller(null, "Jack Black", "jack@gmail.com", dateFormat.parse("23/11/1984"), 2500.00, departmentDao.findById(2));
            sellerDao.insert(seller);
            System.out.println(seller);
        }
        catch(ParseException e){
            System.out.println("Unexpected parse error:");
        }

        System.out.println();
        System.out.println("=== TEST 5 - Update === ");
        seller = sellerDao.findById(2);
        seller.setDepartment(departmentDao.findById(2));
        sellerDao.update(seller);

        System.out.println();
        System.out.println("=== TEST 6 - Delete === ");
        seller = sellerDao.findById(11);
        sellerDao.deleteById(seller.getId());
        seller = sellerDao.findById(10);
        sellerDao.deleteById(seller.getId());

        System.out.println();
        DaoFactory.closeConnection();
    }
}
