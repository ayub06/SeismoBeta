package com.pkm.seismosense.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.apphosting.datastore.EntityV4;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.crypto.KeyAgreementSpi;
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
        name = "pelaporApi",
        version = "v1",
        resource = "pelapor",
        namespace = @ApiNamespace(
                ownerDomain = "backend.seismosense.pkm.com",
                ownerName = "backend.seismosense.pkm.com",
                packagePath = ""
        )
)
public class PelaporEndpoint {

    private static final Logger logger = Logger.getLogger(PelaporEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Pelapor.class);
    }

    /**
     * Returns the {@link Pelapor} with the corresponding ID.
     *
     * @param username the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Pelapor} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "pelapor/{username}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Pelapor get(@Named("username") String username) throws NotFoundException {
        logger.info("Getting Pelapor with ID: " + username);
        Pelapor pelapor = ofy().load().type(Pelapor.class).id(username).now();
        if (pelapor == null) {
            throw new NotFoundException("Could not find Pelapor with ID: " + username);
        }
        return pelapor;
    }


    /**
     * Returns the {@link Pelapor} with the corresponding ID.
     *
     * @param username the ID of the entity to be retrieved
     * @param password the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Pelapor} with the provided ID.
     */

    @ApiMethod(
            name = "loginPelapor",
            path = "login/{username}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Pelapor loginPelapor(@Named("username") String username,@Named("password") String password) throws NotFoundException {

        Pelapor pelapor = ofy().load().type(Pelapor.class).id(username).now();
        if (pelapor == null) {
            throw new NotFoundException("Could not find Pelapor with ID: " + username);
        }

        //PW dijadikan pengangkut response
        if (pelapor.getPassword().equals(password)){
            pelapor.setEmail("1"); //Login Sukses
        }else{
            pelapor.setEmail("0"); //Login Gagal, PW salah
        }
        return pelapor;
    }


    /**
     * Inserts a new {@code Pelapor}.
     */
    @ApiMethod(
            name = "insert",
            path = "pelapor",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Pelapor insert(Pelapor pelapor) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that pelapor.username has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.

        ofy().save().entity(pelapor).now();
        logger.info("Created Pelapor with ID: " + pelapor.getUsername());


        return ofy().load().entity(pelapor).now();
    }

    /**
     * Updates an existing {@code Pelapor}.
     *
     * @param username the ID of the entity to be updated
     * @param pelapor  the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code username} does not correspond to an existing
     *                           {@code Pelapor}
     */
    @ApiMethod(
            name = "update",
            path = "pelapor/{username}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Pelapor update(@Named("username") String username, Pelapor pelapor) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(username);
        ofy().save().entity(pelapor).now();
        logger.info("Updated Pelapor: " + pelapor);
        return ofy().load().entity(pelapor).now();
    }

    /**
     * Deletes the specified {@code Pelapor}.
     *
     * @param username the ID of the entity to delete
     * @throws NotFoundException if the {@code username} does not correspond to an existing
     *                           {@code Pelapor}
     */
    @ApiMethod(
            name = "remove",
            path = "pelapor/{username}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("username") String username) throws NotFoundException {
        checkExists(username);
        ofy().delete().type(Pelapor.class).id(username).now();
        logger.info("Deleted Pelapor with ID: " + username);
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
            path = "pelapor",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Pelapor> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Pelapor> query = ofy().load().type(Pelapor.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Pelapor> queryIterator = query.iterator();
        List<Pelapor> pelaporList = new ArrayList<Pelapor>(limit);
        while (queryIterator.hasNext()) {
            pelaporList.add(queryIterator.next());
        }
        return CollectionResponse.<Pelapor>builder().setItems(pelaporList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String username) throws NotFoundException {
        try {
            ofy().load().type(Pelapor.class).id(username).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Pelapor with ID: " + username);
        }
    }
}