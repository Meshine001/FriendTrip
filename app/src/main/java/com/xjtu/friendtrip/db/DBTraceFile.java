package com.xjtu.friendtrip.db;

import com.xjtu.friendtrip.db.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "FILES".
 */
public class DBTraceFile {

    private Long id;
    private String url;
    private String type;
    private String summary;
    private Integer position;
    private long traceId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DBTraceFileDao myDao;

    private DBTrace dBTrace;
    private Long dBTrace__resolvedKey;


    public DBTraceFile() {
    }

    public DBTraceFile(Long id) {
        this.id = id;
    }

    public DBTraceFile(Long id, String url, String type, String summary, Integer position, long traceId) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.summary = summary;
        this.position = position;
        this.traceId = traceId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBTraceFileDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public long getTraceId() {
        return traceId;
    }

    public void setTraceId(long traceId) {
        this.traceId = traceId;
    }

    /** To-one relationship, resolved on first access. */
    public DBTrace getDBTrace() {
        long __key = this.traceId;
        if (dBTrace__resolvedKey == null || !dBTrace__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBTraceDao targetDao = daoSession.getDBTraceDao();
            DBTrace dBTraceNew = targetDao.load(__key);
            synchronized (this) {
                dBTrace = dBTraceNew;
            	dBTrace__resolvedKey = __key;
            }
        }
        return dBTrace;
    }

    public void setDBTrace(DBTrace dBTrace) {
        if (dBTrace == null) {
            throw new DaoException("To-one property 'traceId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.dBTrace = dBTrace;
            traceId = dBTrace.getId();
            dBTrace__resolvedKey = traceId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
