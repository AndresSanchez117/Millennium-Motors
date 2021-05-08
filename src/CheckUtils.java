import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]+$", Pattern.CASE_INSENSITIVE);

    public static boolean isAlphanumeric(String str)
    {
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetterOrDigit(c) && c != ' ')
                return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    public static boolean isEmail(String str) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(str);
        return matcher.find();
    }

    public static boolean validateClientData(JFrame frame, String rfc, String name, String phone, String email, String address) {
        String message = "";
        boolean isValid = false;

        if (rfc.length() != 13 || !isAlphanumeric(rfc)) {
            message = "RFC invalido.";
        }
        else if (!isAlphanumeric(name) || name.equals("")) {
            message = "Nombre invalido.";
        }
        else if ((phone.length() != 10 && phone.length() != 8) || !isNumeric(phone)) {
            message = "Telefono invalido.";
        }
        else if (!isEmail(email)) {
            message = "Email invalido.";
        }
        // TODO: Validate address
        else if (address.equals("")) {
            message = "Dirección invalida.";
        }
        else {
            isValid = true;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(frame, message);
        }

        return isValid;
    }

    public static boolean validateProviderData(JFrame frame, String rfc, String name, String email) {
        String message = "";
        boolean isValid = false;

        if (rfc.length() != 12 || !isAlphanumeric(rfc)) {
            message = "RFC invalido.";
        }
        else if (!isAlphanumeric(name) || name.equals("")) {
            message = "Nombre ivalido.";
        }
        else if (!isEmail(email)) {
            message = "Email invalido.";
        }
        else {
            isValid = true;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(frame, message);
        }

        return isValid;
    }

    public static boolean validatePartData(JFrame frame, String stock, String brand, String model, String description, String price) {
        String message = "";
        boolean isValid = false;

        int iStock;
        try {
            iStock = Integer.parseInt(stock);
        } catch (NumberFormatException exception) {
            message = "Cantidad invalida.";
            JOptionPane.showMessageDialog(frame, message);
            return false;
        }

        double dPrice;
        try {
            dPrice = Double.parseDouble(price);
        } catch (NumberFormatException exception) {
            message = "Precio invalido.";
            JOptionPane.showMessageDialog(frame, message);
            return false;
        }

        if (iStock < 0) {
            message = "Cantidad invalida.";
        }
        else if (!isAlphanumeric(brand) || brand.equals("")) {
            message = "Marca invalida";
        }
        else if (model.equals("")) {
            message = "Modelo invalido.";
        }
        else if (description.equals("")) {
            message = "Descripcion invalida.";
        }
        else if (dPrice <= 0) {
            message = "Precio invalido.";
        }
        else {
            isValid = true;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(frame, message);
        }

        return isValid;
    }

    public static boolean validateVehicleData(JFrame frame, String serialNumber, String brand, String model, String clientName) {
        String message = "";
        boolean isValid = false;

        if (serialNumber.equals("") || !isNumeric(serialNumber)) {
            message = "Numero de serie invalido.";
        }
        else if (!isAlphanumeric(brand) || brand.equals("")) {
            message = "Marca invalida.";
        }
        else if (model.equals("")) {
            message = "Modelo invalido.";
        }
        else if (clientName.equals("")) {
            message = "Cliente invalido.";
        }
        else {
            isValid = true;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(frame, message);
        }

        return isValid;
    }

    public static boolean validateServiceData(JFrame frame, String clientName, String motive, String workforceCost) {
        String message = "";
        boolean isValid = false;

        double dWorkforceCost;
        try {
            dWorkforceCost = Double.parseDouble(workforceCost);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(frame, "Costo de mano de obra invalido.");
            return false;
        }

        if (clientName.equals("")) {
            message = "Vehículo de cliente invalido.";
        }
        else if (motive.length() < 4) {
            message = "Motivo invalido.";
        }
        else if (dWorkforceCost <= 0) {
            message = "Costo de mano de obra invalido.";
        }
        else {
            isValid = true;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(frame, message);
        }

        return isValid;
    }
}
