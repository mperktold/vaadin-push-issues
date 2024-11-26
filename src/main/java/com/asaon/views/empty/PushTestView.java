package com.asaon.views.empty;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Push Test")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
public class PushTestView extends VerticalLayout {

    private int count;
    private int pushCount;

    private final Span countLabel;
    private final Span pushCountLabel;

    public PushTestView() {
        setSpacing(false);
        add(countLabel = new Span("0"));
        add(pushCountLabel = new Span("0"));
		add(new Button("Increment", e -> {
			count++;
            updateCountLabel();
            UI ui = UI.getCurrent();
            Thread.startVirtualThread(() -> {
				try {
					Thread.sleep(5_000);
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
                    return;
				}
                pushCount++;
                ui.access(() -> {
                    updatePushCountLabel();
                    Notification.show(pushCountLabel.getText());
                });
			});
		}));
    }

    private void updateCountLabel() {
        countLabel.setText(Integer.toString(count));
    }

    private void updatePushCountLabel() {
        pushCountLabel.setText(Integer.toString(pushCount));
    }
}
