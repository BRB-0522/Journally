package com.example.journallybackendtest;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Holds content of associated Content object within RecyclerView
 */
public class RVContentHolder extends RecyclerView.ViewHolder {
    // Contains UI elements
    private View itemView;
    // Text content of Content object
    private EditText txtContent;
    // Associate Content object
    private Content content;

    /**
     * Instantiates a new RVContentHolder object with pass item view
     * @param itemView
     */
    public RVContentHolder(@NonNull View itemView) {
        // Call superclass' constructor
        super(itemView);
        // Set UI elements
        this.itemView = itemView;
        //txtContent = itemView.findViewById(R.id.content_text);
    }

    /**
     * Set details of UI elements to reflect Content object
     * @param content
     */
    public void setDetails(Content content) {
        this.content = content;
        txtContent.setText(content.getContent());
    }

    /**
     * Interface used to implement external code into eventHandlers
     */
    @FunctionalInterface
    public interface eventImplementation {
        void setEvent(View view, Content content);
    }

    /**
     * Set the events of content EditText to execute externally implemented code according to OnFocusChange event.
     * @param event
     */
    public void setHolderEvents(eventImplementation event) {
        txtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // Check whether EditText has gained or lost focus
                if (!b) { // If lost focus
                    // Execute external code
                    event.setEvent(view, content);
                }
            }
        });
    }
}
