package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.ReportTO;

public interface ReportService {

    /**
     * Creates a new report.
     *
     * @param reportTO the {@code ReportTO} object containing the report details
     * @return the created {@code ReportTO} object
     */
    ReportTO createReport(ReportTO reportTO);

    /**
     * Deletes a report by its ID.
     *
     * @param reportId the ID of the report to delete
     */
    void deleteReport(Long reportId);
}
