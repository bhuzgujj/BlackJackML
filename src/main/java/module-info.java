module dev.emileboucher.blackjackml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.net.http;

    // BASE
    exports dev.emileboucher.blackjackml;
    opens dev.emileboucher.blackjackml to javafx.fxml;

    // CONTROLLERS
    exports dev.emileboucher.blackjackml.controllers.ai;
    opens dev.emileboucher.blackjackml.controllers.ai to javafx.fxml;
    exports dev.emileboucher.blackjackml.controllers.player;
    opens dev.emileboucher.blackjackml.controllers.player to javafx.fxml;

    // API
    exports dev.emileboucher.blackjackml.api;
    exports dev.emileboucher.blackjackml.api.models;
    exports dev.emileboucher.blackjackml.models.requests.bodies;
    exports dev.emileboucher.blackjackml.api.requests;
    exports dev.emileboucher.blackjackml.models.requests;

    // MODELS
    exports dev.emileboucher.blackjackml.models;
    opens dev.emileboucher.blackjackml.models to javafx.fxml;
    exports dev.emileboucher.blackjackml.models.tables;
    opens dev.emileboucher.blackjackml.models.tables to javafx.fxml;
    exports dev.emileboucher.blackjackml.models.datamodels;
    exports dev.emileboucher.blackjackml.models.responses;

    // DATA MANAGERS
    exports dev.emileboucher.blackjackml.files;

    // HANDLERS
    exports dev.emileboucher.blackjackml.gamehandlers;
}