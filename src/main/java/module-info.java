module dev.emileboucher.blackjackml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens dev.emileboucher.blackjackml to javafx.fxml;
    exports dev.emileboucher.blackjackml;
}