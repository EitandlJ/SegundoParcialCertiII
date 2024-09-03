import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutTests extends BaseTest {

    //TEST 1

    @Test
    public void checkoutWithEmptyFieldsTest() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Añadir producto al carrito
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.btn_inventory")));
        addToCartButton.click();

        // Ir al carrito de compras
        WebElement shoppingCartIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        shoppingCartIcon.click();

        // Click en el botón de Checkout
        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_action.checkout_button")));
        checkoutButton.click();

        // Ingresar datos vacíos en el checkout
        WebElement firstNameInput = driver.findElement(By.id("first-name"));
        firstNameInput.sendKeys(" ");

        WebElement lastNameInput = driver.findElement(By.id("last-name"));
        lastNameInput.sendKeys(" ");

        WebElement postalCodeInput = driver.findElement(By.id("postal-code"));
        postalCodeInput.sendKeys(" ");

        // Enviar formulario
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.cart_button")));
        continueButton.click();

        // Verificar que no se muestra el subheader "Checkout: Overview"
        WebElement subheader = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("subheader")));

        String expectedSubheaderText = "Checkout: Overview";
        String actualSubheaderText = subheader.getText();

        // Verifica que el subheader no esté presente si los datos ingresados son inválidos
        Assertions.assertNotEquals(expectedSubheaderText, actualSubheaderText, "The checkout subheader should not be displayed with invalid input.");
    }

    //TEST 2

    @Test
    public void checkoutWithInvalidPostalCodeTest() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Añadir producto al carrito
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.btn_inventory")));
        addToCartButton.click();

        // Ir al carrito de compras
        WebElement shoppingCartIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        shoppingCartIcon.click();

        // Click en el botón de Checkout
        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_action.checkout_button")));
        checkoutButton.click();

        // Ingresar datos válidos pero incorrectos en el checkout
        WebElement firstNameInput = driver.findElement(By.id("first-name"));
        firstNameInput.sendKeys("nombre");

        WebElement lastNameInput = driver.findElement(By.id("last-name"));
        lastNameInput.sendKeys("apellido");

        WebElement postalCodeInput = driver.findElement(By.id("postal-code"));
        postalCodeInput.sendKeys("codigo postal");  // Código postal no numérico

        // Enviar formulario
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.cart_button")));
        continueButton.click();

        // Verificar que no se muestra el subheader "Checkout: Overview"
        WebElement subheader = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("subheader")));

        String expectedSubheaderText = "Checkout: Overview";
        String actualSubheaderText = subheader.getText();

        // Verifica que el subheader no esté presente si el código postal es inválido
        Assertions.assertNotEquals(expectedSubheaderText, actualSubheaderText, "The checkout subheader should not be displayed with invalid postal code.");
    }

    // Test 3

    @Test
    public void checkoutWithEmptyFieldsErrorMessageTest() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Añadir producto al carrito
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.btn_inventory")));
        addToCartButton.click();

        // Ir al carrito de compras
        WebElement shoppingCartIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        shoppingCartIcon.click();

        // Click en el botón de Checkout
        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_action.checkout_button")));
        checkoutButton.click();

        // Dejar los campos en blanco

        // Enviar formulario
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.cart_button")));
        continueButton.click();

        // Verificar el mensaje de error
        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));

        String expectedErrorMessage = "Error: First Name is required";
        String actualErrorMessage = errorMessage.getText();

        // Verifica que el mensaje de error sea el esperado
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "The error message should indicate that the first name is required.");
    }

}
