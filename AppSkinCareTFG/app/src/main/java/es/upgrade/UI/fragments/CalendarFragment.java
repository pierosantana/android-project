package es.upgrade.UI.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.prolificinteractive.materialcalendarview.*;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

import es.upgrade.R;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class CalendarFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private Button btnAddEvent, btnViewEvent;
    private LinearLayout eventsContainer;

    private DatabaseReference eventsRef;
    private CalendarDay selectedDate;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final Set<CalendarDay> eventDates = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        btnAddEvent = view.findViewById(R.id.btnAddEvent);
        btnViewEvent = view.findViewById(R.id.btnViewEvents);
        eventsContainer = view.findViewById(R.id.eventsContainer);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        eventsRef = FirebaseDatabase.getInstance().getReference("CalendarioEventos").child(userId);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = date;
            refreshDecorators();
        });

        btnAddEvent.setOnClickListener(v -> {
            if (selectedDate != null) showAddEventDialog();
            else Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
        });

        btnViewEvent.setOnClickListener(v -> {
            if (selectedDate != null) {
                String date = dateFormat.format(selectedDate.getDate());
                showEvents(date);
            } else {
                Toast.makeText(getContext(), "Please select a date first", Toast.LENGTH_SHORT).show();
            }
        });

        refreshDecorators();

        return view;
    }

    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("New Event");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        EditText titleInput = new EditText(getContext());
        titleInput.setHint("Event title");
        layout.addView(titleInput);

        EditText timeInput = new EditText(getContext());
        timeInput.setHint("Time (e.g., 10:30)");
        timeInput.setInputType(InputType.TYPE_CLASS_DATETIME);
        layout.addView(timeInput);

        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String time = timeInput.getText().toString().trim();

            if (!title.isEmpty()) {
                String date = dateFormat.format(selectedDate.getDate());
                String id = eventsRef.child(date).push().getKey();

                Map<String, String> event = new HashMap<>();
                event.put("title", title);
                event.put("hour", time);

                if (id != null) {
                    eventsRef.child(date).child(id).setValue(event);
                    Toast.makeText(getContext(), "Event saved", Toast.LENGTH_SHORT).show();
                    refreshDecorators();
                }
            } else {
                Toast.makeText(getContext(), "Title is required", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showEvents(String date) {
        eventsContainer.removeAllViews();

        eventsRef.child(date).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                for (DataSnapshot eventSnap : snapshot.getChildren()) {
                    String id = eventSnap.getKey();
                    Map<String, String> data = (Map<String, String>) eventSnap.getValue();

                    if (data != null) {
                        String title = data.getOrDefault("title", "Untitled");
                        String time = data.getOrDefault("hour", "No time");

                        View item = LayoutInflater.from(getContext()).inflate(R.layout.item_evento_inline, eventsContainer, false);
                        TextView infoText = item.findViewById(R.id.txtEventInfo);
                        Button btnEdit = item.findViewById(R.id.btnEdit);
                        Button btnDelete = item.findViewById(R.id.btnDelete);

                        infoText.setText("• " + title + " - " + time);

                        btnEdit.setOnClickListener(v -> showEditEventDialog(date, id, data));
                        btnDelete.setOnClickListener(v -> {
                            eventsRef.child(date).child(id).removeValue();
                            eventsContainer.removeView(item);
                            Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                            refreshDecorators();
                        });

                        eventsContainer.addView(item);
                    }
                }
            } else {
                Toast.makeText(getContext(), "No events for this date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditEventDialog(String date, String id, Map<String, String> originalData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Event");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        EditText titleInput = new EditText(getContext());
        titleInput.setText(originalData.get("title"));
        layout.addView(titleInput);

        EditText timeInput = new EditText(getContext());
        timeInput.setText(originalData.get("hour"));
        layout.addView(timeInput);

        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
            Map<String, String> updated = new HashMap<>();
            updated.put("title", titleInput.getText().toString().trim());
            updated.put("hour", timeInput.getText().toString().trim());

            eventsRef.child(date).child(id).setValue(updated);
            Toast.makeText(getContext(), "Event updated", Toast.LENGTH_SHORT).show();
            showEvents(date);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void refreshDecorators() {
        eventsRef.get().addOnSuccessListener(snapshot -> {
            calendarView.removeDecorators();
            eventDates.clear();

            for (DataSnapshot dateSnap : snapshot.getChildren()) {
                String dateStr = dateSnap.getKey();
                try {
                    Date date = dateFormat.parse(dateStr);
                    if (date != null) {
                        eventDates.add(CalendarDay.from(date));
                    }
                } catch (Exception ignored) {}
            }

            calendarView.addDecorator(new TurquoiseEventDecorator(eventDates));
            if (selectedDate != null) {
                calendarView.addDecorator(new WhiteSelectedEventDecorator(selectedDate, eventDates));
            }
        });
    }

    static class TurquoiseEventDecorator implements DayViewDecorator {
        private final Set<CalendarDay> dates;

        TurquoiseEventDecorator(Set<CalendarDay> dates) {
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(8, 0xFF44BBA4));
        }
    }

    static class WhiteSelectedEventDecorator implements DayViewDecorator {
        private final CalendarDay selectedDay;
        private final Set<CalendarDay> dates;

        WhiteSelectedEventDecorator(CalendarDay selectedDay, Set<CalendarDay> dates) {
            this.selectedDay = selectedDay;
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(selectedDay) && dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(8, 0xFFFFFFFF));
        }
    }
}