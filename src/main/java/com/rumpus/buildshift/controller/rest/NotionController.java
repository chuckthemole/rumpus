package com.rumpus.buildshift.controller.rest;

import com.rumpus.common.Controller.Integrations.AbstractNotionIntegrationController;
import com.rumpus.common.Integrations.NotionIntegration;

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
    public NotionController(Map<String, NotionIntegration> notionMap) {
        super(notionMap);
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
    //         @PathVariable String databaseId,
    //         @RequestBody String jsonBody
    // ) throws Exception {
    //     return getNotionIntegration().createPage(databaseId, jsonBody);
    // }

    // TODO: Add additional endpoints here, e.g., updatePage, queryDatabase, deletePage, etc.
}

