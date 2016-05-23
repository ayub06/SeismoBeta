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
        name = "korbanApi",
        version = "v1",
        resource = "korban",
        namespace = @ApiNamespace(
                ownerDomain = "backend.seismosense.pkm.com",
                ownerName = "backend.seismosense.pkm.com",
                packagePath = ""
        )
)
public class KorbanEndpoint {

    private static final Logger logger = Logger.getLogger(KorbanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Korban.class);
    }

    /**
     * Returns the {@link Korban} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Korban} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "korban/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Korban get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Korban with ID: " + id);
        Korban korban = ofy().load().type(Korban.class).id(id).now();
        if (korban == null) {
            throw new NotFoundException("Could not find Korban with ID: " + id);
        }
        return korban;
    }

    /**
     * Inserts a new {@code Korban}.
     */
    @ApiMethod(
            name = "insert",
            path = "korban",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Korban insert(Korban korban) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that korban.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(korban).now();
        logger.info("Created Korban with ID: " + korban.getId());

        return ofy().load().entity(korban).now();
    }

    /**
     * Updates an existing {@code Korban}.
     *
     * @param id     the ID of the entity to be updated
     * @param korban the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Korban}
     */
    @ApiMethod(
            name = "update",
            path = "korban/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Korban update(@Named("id") Long id, Korban korban) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(korban).now();
        logger.info("Updated Korban: " + korban);
        return ofy().load().entity(korban).now();
    }

    /**
     * Deletes the specified {@code Korban}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Korban}
     */
    @ApiMethod(
            name = "remove",
            path = "korban/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Korban.class).id(id).now();
        logger.info("Deleted Korban with ID: " + id);
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
            path = "korban",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Korban> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Korban> query = ofy().load().type(Korban.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Korban> queryIterator = query.iterator();
        List<Korban> korbanList = new ArrayList<Korban>(limit);
        while (queryIterator.hasNext()) {
            korbanList.add(queryIterator.next());
        }
        return CollectionResponse.<Korban>builder().setItems(korbanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Korban.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Korban with ID: " + id);
        }
    }
}