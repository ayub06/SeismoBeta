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
        name = "bantuanApi",
        version = "v1",
        resource = "bantuan",
        namespace = @ApiNamespace(
                ownerDomain = "backend.seismosense.pkm.com",
                ownerName = "backend.seismosense.pkm.com",
                packagePath = ""
        )
)
public class BantuanEndpoint {

    private static final Logger logger = Logger.getLogger(BantuanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Bantuan.class);
    }

    /**
     * Returns the {@link Bantuan} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Bantuan} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bantuan/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Bantuan get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Bantuan with ID: " + id);
        Bantuan bantuan = ofy().load().type(Bantuan.class).id(id).now();
        if (bantuan == null) {
            throw new NotFoundException("Could not find Bantuan with ID: " + id);
        }
        return bantuan;
    }

    /**
     * Inserts a new {@code Bantuan}.
     */
    @ApiMethod(
            name = "insert",
            path = "bantuan",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Bantuan insert(Bantuan bantuan) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bantuan.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(bantuan).now();
        logger.info("Created Bantuan with ID: " + bantuan.getId());

        return ofy().load().entity(bantuan).now();
    }

    /**
     * Updates an existing {@code Bantuan}.
     *
     * @param id      the ID of the entity to be updated
     * @param bantuan the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bantuan}
     */
    @ApiMethod(
            name = "update",
            path = "bantuan/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Bantuan update(@Named("id") Long id, Bantuan bantuan) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(bantuan).now();
        logger.info("Updated Bantuan: " + bantuan);
        return ofy().load().entity(bantuan).now();
    }

    /**
     * Deletes the specified {@code Bantuan}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bantuan}
     */
    @ApiMethod(
            name = "remove",
            path = "bantuan/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Bantuan.class).id(id).now();
        logger.info("Deleted Bantuan with ID: " + id);
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
            path = "bantuan",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bantuan> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bantuan> query = ofy().load().type(Bantuan.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bantuan> queryIterator = query.iterator();
        List<Bantuan> bantuanList = new ArrayList<Bantuan>(limit);
        while (queryIterator.hasNext()) {
            bantuanList.add(queryIterator.next());
        }
        return CollectionResponse.<Bantuan>builder().setItems(bantuanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Bantuan.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bantuan with ID: " + id);
        }
    }
}