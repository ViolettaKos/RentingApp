package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.CommandUtil.redirectCommand;
import static com.example.rentingapp.web.command.constants.Commands.SHOW_ADMIN_CARS;
import static com.example.rentingapp.web.command.constants.Path.*;

@MultipartConfig
public class AddCarCommand implements Command {
    private static final Logger LOG = Logger.getLogger(AddCarCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return CommandType.GET==commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String brand = req.getParameter(BRAND);
        String name = req.getParameter(NAME);
        String quality = req.getParameter(QUALITY);
        int price = Integer.parseInt(req.getParameter(PRICE));
        String path = ADMIN_CARS_PAGE;
        Car car=new Car(brand, quality, name, price, true);
        CarsService carsService=ServiceFactory.getCarsService();
        carsService.addCar(car);
        String fileName= carsService.getLastId();
        try {
            imgProcessing(req, fileName, brand);
        } catch (ServletException | IOException e) {
            LOG.trace("Error in extracting parts");
            path = ERROR_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return redirectCommand(SHOW_ADMIN_CARS);
    }

    private void imgProcessing(HttpServletRequest req, String fileName, String brand) throws ServletException, IOException {
        Collection<Part> parts=req.getParts();
        String path;
        for (Part part : parts) {
            if (part.getName().equals(FILE)) {
                LOG.trace("Upload car img");
                path = CAR_IMG;
                uploadFile(part, fileName, path);
            }
            if(part.getName().equals(LOGO)) {
                LOG.trace("File logo upload");
                path = BRAND_IMG;
                uploadFile(part, brand.toLowerCase(), path);
            }
        }
    }

    private void uploadFile(Part filePart, String fileName, String path) {
        try (OutputStream otpStream = Files.newOutputStream(new File(path + File.separator + fileName + ".jpg").toPath());
             InputStream iptStream = filePart.getInputStream()) {
            // initialize instances of OutputStream and InputStream classes
            int read = 0;

            // initialize bytes array for storing file data
            final byte[] bytes = new byte[1024];

            // use while loop to read data from the file using iptStream and write into  the desination folder using writer and otpStream
            while ((read = iptStream.read(bytes)) != -1) {
                otpStream.write(bytes, 0, read);
            }

        } catch (IOException e) {
            LOG.trace("Failed to upload file"+e);
        }
    }

    private String doGet(HttpServletRequest request) throws ServiceException {
        CarsService carsService= ServiceFactory.getCarsService();
        request.setAttribute(BRAND, carsService.getBrands());
        return ADD_CAR_PAGE;
    }
}
