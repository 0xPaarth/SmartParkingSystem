package com.parking.app.control;

import com.parking.app.events.NotificationEvent;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public final class ToastNotification {
  private ToastNotification() {}
  public static void show(Window owner, NotificationEvent event) {
    if (owner == null) return;
    VBox content = new VBox(4, new Label(event.title()), new Label(event.message()));
    content.setAlignment(Pos.CENTER_LEFT);
    content.getStyleClass().addAll("toast", "toast-" + event.type().name().toLowerCase());
    Popup popup = new Popup();
    popup.getContent().add(content);
    popup.setAutoHide(true);
    popup.show(owner, owner.getX() + owner.getWidth() - 360, owner.getY() + owner.getHeight() - 120);
    PauseTransition timeout = new PauseTransition(Duration.seconds(4));
    timeout.setOnFinished(ignored -> popup.hide());
    timeout.play();
  }
}
