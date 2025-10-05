package com.rumpus.buildshift.controller.rest;

import com.rumpus.common.Controller.Integrations.AbstractNotionIntegrationController;
import com.rumpus.common.Integrations.NotionIntegration;
import com.rumpus.common.Integrations.NotionIntegrationRegistry;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Concrete REST controller for Notion integration.
 * <p>
 * Extends {@link AbstractNotionIntegrationController} and exposes endpoints to
 * query, create, and update Notion database pages.
 * </p>
 */
@RestController
@RequestMapping("/notion-api")
public class NotionController extends AbstractNotionIntegrationController {

    /**
     * Constructor for NotionController.
     *
     * @param notionMap The Notion integration bean, injected via Spring.
     */
    @Autowired
    public NotionController(
            Map<String, NotionIntegration> notionMap,
            NotionIntegrationRegistry notionRegistry) {
        super(
                notionMap,
                notionRegistry);
        this.debugNotionMap();
        this.debugNotionRegistry();
    }

    /**
     * Example endpoint to create a new page in a Notion database.
     * <p>
     * Consumer app sends a JSON body matching Notion page properties format.
     * </p>
     *
     * @param databaseId The ID of the Notion database.
     * @param jsonBody   The JSON body representing page properties.
     * @return The raw JSON response from the Notion API.
     * @throws Exception If an error occurs while creating the page.
     */
    // @PostMapping("/database/{databaseId}/page")
    // public String createPage(
    // @PathVariable String databaseId,
    // @RequestBody String jsonBody
    // ) throws Exception {
    // return getNotionIntegration().createPage(databaseId, jsonBody);
    // }

    // TODO: Add additional endpoints here, e.g., updatePage, queryDatabase,
    // deletePage, etc.

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NotionController {")
                .append("class=").append(this.getClass().getSimpleName());

        try {
            // Reflectively access fields from AbstractNotionIntegrationController
            var fieldMap = AbstractNotionIntegrationController.class.getDeclaredField("notionMap");
            fieldMap.setAccessible(true);
            @SuppressWarnings("unchecked") // TODO: look into
            Map<String, NotionIntegration> notionMap = (Map<String, NotionIntegration>) fieldMap.get(this);

            var fieldRegistry = AbstractNotionIntegrationController.class.getDeclaredField("notionRegistry");
            fieldRegistry.setAccessible(true);
            Object notionRegistry = fieldRegistry.get(this);

            sb.append(", notionMapSize=").append(notionMap != null ? notionMap.size() : "null");
            sb.append(", notionMapKeys=").append(notionMap != null ? notionMap.keySet() : "null");
            sb.append(", notionRegistryInjected=").append(notionRegistry != null);
        } catch (Exception e) {
            sb.append(", errorReadingFields=").append(e.getMessage());
        }

        sb.append(" }");
        return sb.toString();
    }

}
