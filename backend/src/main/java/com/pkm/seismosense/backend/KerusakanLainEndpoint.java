package com.pkm.seismosense.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "kerusakanLainApi",
        version = "v1",
        resource = "kerusakanLain",
        namespace = @ApiNamespace(
                ownerDomain = "backend.seismosense.pkm.com",
                ownerName = "backend.seismosense.pkm.com",
                packagePath = ""
        )
)
public class KerusakanLainEndpoint {

    private static final Logger logger = Logger.getLogger(KerusakanLainEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(KerusakanLain.class);
    }

    /**
     * Returns the {@link KerusakanLain} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code KerusakanLain} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "kerusakanLain/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public KerusakanLain get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting KerusakanLain with ID: " + id);
        KerusakanLain kerusakanLain = ofy().load().type(KerusakanLain.class).id(id).now();
        if (kerusakanLain == null) {
            throw new NotFoundException("Could not find KerusakanLain with ID: " + id);
        }
        return kerusakanLain;
    }

    /**
     * Inserts a new {@code KerusakanLain}.
     */
    @ApiMethod(
            name = "insert",
            path = "kerusakanLain",
            httpMethod = ApiMethod.HttpMethod.POST)
    public KerusakanLain insert(KerusakanLain kerusakanLain) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that kerusakanLain.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(kerusakanLain).now();
        logger.info("Created KerusakanLain with ID: " + kerusakanLain.getId());

        return ofy().load().entity(kerusakanLain).now();
    }

    /**
     * Updates an existing {@code KerusakanLain}.
     *
     * @param id            the ID of the entity to be updated
     * @param kerusakanLain the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code KerusakanLain}
     */
    @ApiMethod(
            name = "update",
            path = "kerusakanLain/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public KerusakanLain update(@Named("id") Long id, KerusakanLain kerusakanLain) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(kerusakanLain).now();
        logger.info("Updated KerusakanLain: " + kerusakanLain);
        return ofy().load().entity(kerusakanLain).now();
    }

    /**
     * Deletes the specified {@code KerusakanLain}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code KerusakanLain}
     */
    @ApiMethod(
            name = "remove",
            path = "kerusakanLain/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(KerusakanLain.class).id(id).now();
        logger.info("Deleted KerusakanLain with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "kerusakanLain",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<KerusakanLain> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<KerusakanLain> query = ofy().load().type(KerusakanLain.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<KerusakanLain> queryIterator = query.iterator();
        List<KerusakanLain> kerusakanLainList = new ArrayList<KerusakanLain>(limit);
        while (queryIterator.hasNext()) {
            kerusakanLainList.add(queryIterator.next());
        }
        return CollectionResponse.<KerusakanLain>builder().setItems(kerusakanLainList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(KerusakanLain.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find KerusakanLain with ID: " + id);
        }
    }
}