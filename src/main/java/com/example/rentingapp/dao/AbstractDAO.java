package com.example.rentingapp.dao;


import com.example.rentingapp.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;


import javax.sql.DataSource;

/**

 The AbstractDAO class is an abstract class that serves as the base for DAO factory classes
 providing abstract methods to obtain DAO instances for Users, Cars, and Orders.
 */
public abstract class AbstractDAO {
    private static AbstractDAO instance;
    private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

    public abstract UserDAO getUserDAO();

    public abstract CarDAO getCarDAO();

    public abstract OrderDAO getOrderDAO();


    /**
     Returns the singleton instance of the DAO factory.
     @return the singleton instance of the DAO factory.
     */
    public static synchronized AbstractDAO getInstance() {
        if (instance == null) {
            LOG.trace("Instance null");
            instance = new DAOFactory(ConnectionPool.getDataSource());
        }
        return instance;
    }
}
