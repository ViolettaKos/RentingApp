package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.IncorrectDataException;
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
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String path = ADMIN_CARS_PAGE;
        try {
            if (checkIsEmpty(req))
                throw new IncorrectDataException();
            String brand = req.getParameter(BRAND);
            String name = req.getParameter(NAME);
            String quality = req.getParameter(QUALITY);
            int price = Integer.parseInt(req.getParameter(PRICE));
            Car car = new Car(brand, quality, name, price);
            CarsService carsService = ServiceFactory.getCarsService();
            carsService.addCar(car);
            String fileName = carsService.getLastId();
            imgProcessing(req, fileName, brand);
        } catch (ServletException | IncorrectDataException | IOException e) {
            LOG.error("Error in extracting parts");
            path = ERROR_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return redirectCommand(SHOW_ADMIN_CARS);
    }


    /**
     * This method processes the uploaded images of a car and its brand logo.
     * It takes in the HttpServletRequest object, file name as ID of last inserted car in table and brand name to upload
     * the images to their paths.
     * @param req the HttpServletRequest object which contains the uploaded images
     * @param fileName the name of the file to be uploaded
     * @param brand the brand name of the car for which the logo will be uploaded
     * @throws ServletException if there is an issue with the servlet
     * @throws IOException if there is an issue with the I/O
     */
    private void imgProcessing(HttpServletRequest req, String fileName, String brand) throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        String path;
        for (Part part : parts) {
            if (part.getName().equals(FILE)) {
                LOG.trace("Upload car img");
                path = CAR_IMG;
                uploadFile(part, fileName, path);
            }
            if (part.getName().equals(LOGO)) {
                LOG.trace("File logo upload");
                path = BRAND_IMG;
                uploadFile(part, brand.toLowerCase(), path);
            }
        }
    }

    /**
     * A utility method for uploading a file to the specified path.
     * @param filePart the part of the HTTP request containing the file to upload
     * @param fileName the name of the file to upload
     * @param path the path where the file will be uploaded to
     */
    private void uploadFile(Part filePart, String fileName, String path) {
        try (OutputStream otpStream = Files.newOutputStream(new File(path + File.separator + fileName + ".jpg").toPath());
             InputStream iptStream = filePart.getInputStream()) {
            int read;

            // initialize bytes array for storing file data
            final byte[] bytes = new byte[1024];

            // use while loop to read data from the file using iptStream and write into  the destination folder using writer and otpStream
            while ((read = iptStream.read(bytes)) != -1) {
                otpStream.write(bytes, 0, read);
            }

        } catch (IOException e) {
            LOG.error("Failed to upload file" + e);
        }
    }

    private String doGet(HttpServletRequest request) throws ServiceException {
        CarsService carsService = ServiceFactory.getCarsService();
        request.setAttribute(BRAND, carsService.getBrands());
        return ADD_CAR_PAGE;
    }

    private static boolean checkIsEmpty(HttpServletRequest req) {
        return !req.getParameter(BRAND).isEmpty() || !req.getParameter(NAME).isEmpty() || !req.getParameter(PRICE).isEmpty()
                || !req.getParameter(QUALITY).isEmpty();
    }
}
