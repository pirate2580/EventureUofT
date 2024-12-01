package app.use_case.filter_event;

import java.util.List;

/**
 * Input Data for the Filter Event Use Case.
 * This class serves as a data transfer object (DTO) for capturing the filtering criteria
 * used in the Filter Event use case. It encapsulates event categories (tags) for filtering purposes.
 */
public class FilterEventInputData {
    private final List<String> tags;

    /**
     * Constructs a new {@link FilterEventInputData}.
     * @param category (tag) A list of event categories (tags) to filter by. Examples include "clubs",
     *             "parties", "sports", etc. Must not be {@code null}.
     */
    public FilterEventInputData(List<String> category) {
        this.tags = category;
    }

    /**
     * Retrieves the list of event categories (tags) to filter by.
     * @return A list of event tags.
     */
    public List<String> getTags() {
        return tags;
    }
}
