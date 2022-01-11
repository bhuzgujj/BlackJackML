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

    // API
    exports dev.emileboucher.blackjackml.api;
    exports dev.emileboucher.blackjackml.api.models;
    exports dev.emileboucher.blackjackml.models.requests.bodies;
    exports dev.emileboucher.blackjackml.api.requests;
    exports dev.emileboucher.blackjackml.models.requests;

    // MODELS
    exports dev.emileboucher.blackjackml.models;
    exports dev.emileboucher.blackjackml.models.datamodels;
    exports dev.emileboucher.blackjackml.models.responses;

    // DATAMANAGERS
    exports dev.emileboucher.blackjackml.files;

    exports dev.emileboucher.blackjackml.gamehandlers;
    opens dev.emileboucher.blackjackml.models to javafx.fxml;
}