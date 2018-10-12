package com.epam.lab.utils.parser;

import com.epam.lab.utils.parser.XML_models.User;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.epam.lab.constants.Constants.*;

public class MyParser {

    private static final Logger LOG = Logger.getLogger(MyParser.class);

    public static Object[] parseXML(String path) {
        List<User> users = new ArrayList<>();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
            NodeList usersNodeList = document.getElementsByTagName(USER);
            for (int i = 0; i < usersNodeList.getLength(); i++) {
                Element singleUser = (Element) usersNodeList.item(i);
                users.add(new User.UserBuilder()
                        .setEmail(singleUser.getElementsByTagName(EMAIL).item(0).getChildNodes().item(0).getNodeValue())
                        .setPassword(singleUser.getElementsByTagName(PASSWORD).item(0).getChildNodes().item(0).getNodeValue())
                        .build());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return users.toArray();
    }

    @SuppressWarnings(value = "unchecked")
    public static Object[] parseCSV(String path) {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                String[] singleUserData = line.split(COMMA);
                User.UserBuilder userBuilder = new User.UserBuilder();
                userBuilder.setEmail(singleUserData[0]).setPassword(singleUserData[1]);
                User user = userBuilder.build();
                users.add(user);
            }
            return users.toArray();
        } catch (IOException e) {
            LOG.error(e);
            return null;
        }
    }

    public static Object[] parseEXCEL(String path) {
        List<User> users = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                User.UserBuilder user = new User.UserBuilder();
                Row row = sheet.getRow(i);
                users.add(user
                        .setEmail(row.getCell(0).getStringCellValue())
                        .setPassword(row.getCell(1).getStringCellValue())
                        .build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users.toArray();
    }


    public static Properties parsePropertiesFile(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            LOG.error(e);
        }
        return properties;
    }
}
