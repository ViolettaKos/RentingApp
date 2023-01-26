package com.example.rentingapp.dao;


import com.example.rentingapp.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;


import javax.sql.DataSource;


public abstract class AbstractDAO {

    private static AbstractDAO instance;
    //private static DataSource dataSource;

    private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

//    protected AbstractDAO() {
//        dataSource=ConnectionPool.getDataSource();
//        LOG.trace("Datasource in constructor: "+dataSource.toString());
//    }

    public abstract UserDAO getUserDAO();

    public abstract CarDAO getCarDAO();

    public abstract OrderDAO getOrderDAO();


    public static synchronized AbstractDAO getInstance() {
        if (instance == null) {
            LOG.trace("Instance null");
            instance = new DAOFactory(ConnectionPool.getDataSource()); }
        return instance;
    }
}
