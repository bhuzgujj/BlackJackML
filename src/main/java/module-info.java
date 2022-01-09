module dev.emileboucher.blackjackml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.net.http;
    requires com.google.gson;

    opens dev.emileboucher.blackjackml to javafx.fxml;
    exports dev.emileboucher.blackjackml;
    exports dev.emileboucher.blackjackml.controllers;
    opens dev.emileboucher.blackjackml.controllers to javafx.fxml;
    exports dev.emileboucher.blackjackml.api;
    exports dev.emileboucher.blackjackml.api.models;
    exports dev.emileboucher.blackjackml.api.models.bodies;
    exports dev.emileboucher.blackjackml.api.requests.abstracts;
    exports dev.emileboucher.blackjackml.api.requests.concretes;
    exports dev.emileboucher.blackjackml.models;
    opens dev.emileboucher.blackjackml.models to javafx.fxml;
}