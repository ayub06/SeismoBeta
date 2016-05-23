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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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
        name = "laporanApi",
        version = "v1",
        resource = "laporan",
        namespace = @ApiNamespace(
                ownerDomain = "backend.seismosense.pkm.com",
                ownerName = "backend.seismosense.pkm.com",
                packagePath = ""
        )
)
public class LaporanEndpoint {

    private static final int DEFAULT_LIST_LIMIT = 20;
    public  static long countData=0;
    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Laporan.class);
    }

    /**
     * Returns the {@link Laporan} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Laporan} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "laporan/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Laporan get(@Named("id") Long id) throws NotFoundException {
        Laporan laporan = ofy().load().type(Laporan.class).id(id).now();
        if (laporan == null) {
            throw new NotFoundException("Could not find Laporan with ID: " + id);
        }
        return laporan;
    }

    @ApiMethod(
            name = "insert",
            path = "laporan",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Laporan insert(Laporan laporan) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that laporan.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.

        laporan.setId(Calendar.getInstance().getTimeInMillis());
        ofy().save().entity(laporan).now();

        return ofy().load().entity(laporan).now();
    }

    /**
     * Updates an existing {@code Laporan}.
     *
     * @param id      the ID of the entity to be updated
     * @param laporan the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Laporan}
     */
    @ApiMethod(
            name = "update",
            path = "laporan/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Laporan update(@Named("id") Long id, Laporan laporan) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(laporan).now();
        return ofy().load().entity(laporan).now();
    }

    /**
     * Deletes the specified {@code Laporan}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Laporan}
     */
    @ApiMethod(
            name = "remove",
            path = "laporan/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Laporan.class).id(id).now();
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
            path = "laporan",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Laporan> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit, @Named("idGempa") String idGempa) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Laporan> query = ofy().load().type(Laporan.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Laporan> queryIterator = query.iterator();
        List<Laporan> laporanList = new ArrayList<Laporan>(limit);
        while (queryIterator.hasNext()) {
            Laporan laporan=queryIterator.next();
            if (laporan.getGempaId().equals(idGempa)){
                laporanList.add(laporan);
            }
        }

        return CollectionResponse.<Laporan>builder().setItems(laporanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Laporan.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Laporan with ID: " + id);
        }
    }

}