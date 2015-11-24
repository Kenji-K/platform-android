package com.ushahidi.platform.mobile.app.data.database;

import com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity;
import com.ushahidi.platform.mobile.app.data.exception.DeploymentNotFoundException;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class DeploymentDatabaseHelper extends BaseDatabaseHelper {

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    @Inject
    public DeploymentDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Gets deployments by it's status
     *
     * @param status The status to use to query for the deployment
     * @return An Observable that emits a {@link DeploymentEntity}
     */
    public Observable<DeploymentEntity> getByStatus(final DeploymentEntity.Status status) {
        return Observable.create((subscriber) -> {
            final DeploymentEntity deploymentEntity = get(status);
            if (deploymentEntity != null) {
                subscriber.onNext(deploymentEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    /**
     * Gets deployment lists
     *
     * @return An Observable that emits a list of {@link DeploymentEntity}
     */
    public Observable<List<DeploymentEntity>> getDeployments() {
        return Observable.create(subscriber -> {
            final List<DeploymentEntity> deploymentEntities = cupboard()
                    .withDatabase(getReadableDatabase()).query(DeploymentEntity.class).list();
            if (deploymentEntities != null) {
                subscriber.onNext(deploymentEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    /**
     * Gets a deployment
     *
     * @param id The ID of the deployment to retrieve
     * @return An Observable that emits a {@link DeploymentEntity}
     */
    public Observable<DeploymentEntity> getDeployment(Long id) {
        return Observable.create(subscriber -> {
            final DeploymentEntity deploymentEntity = cupboard().withDatabase(getReadableDatabase())
                    .query(DeploymentEntity.class)
                    .byId(id).get();
            if (deploymentEntity != null) {
                subscriber.onNext(deploymentEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DeploymentNotFoundException());
            }
        });
    }

    /**
     * Saves a {@link DeploymentEntity} into the db
     *
     * @param deploymentEntity The deployment to save to the db
     * @return The row affected
     */
    public Observable<Long> put(DeploymentEntity deploymentEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Long row = null;
                try {
                    row = cupboard().withDatabase(getWritableDatabase()).put(deploymentEntity);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(row);
                subscriber.onCompleted();
            }

        });
    }

    /**
     * Deletes a {@link DeploymentEntity} from the db
     *
     * @param deploymentId The deployment to be deleted from the db
     * @return The row affected. One means successful otherwise it triggers an error
     */
    public Observable<Long> deleteDeployment(Long deploymentId) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                boolean deleted = false;
                try {
                    deleted = cupboard().withDatabase(getWritableDatabase())
                            .delete(DeploymentEntity.class, deploymentId);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                if (deleted) {
                    subscriber.onNext(1l);
                } else {
                    subscriber.onError(new Exception());
                }
                subscriber.onCompleted();
            }

        });
    }

    private DeploymentEntity get(final DeploymentEntity.Status status) {
        final DeploymentEntity deploymentEntity = cupboard()
                .withDatabase(getReadableDatabase()).query(DeploymentEntity.class)
                .withSelection("mStatus = ?", status.name()).get();
        return deploymentEntity;
    }
}
