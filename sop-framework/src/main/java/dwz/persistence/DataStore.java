package dwz.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dwz.dal.BaseMapper;
import dwz.dal.object.AbstractDO;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:35
 * @Package: dwz.persistence
 */

public class DataStore<T extends AbstractDO> {
    private List<T> _items;
    
    private List<T> items;

    private List<T> addItems;
    
    private List<T> updItems;
    
    private List<T> delItems;

    public DataStore(List<T> items) {
        this._items = items;
    }

    public List<T> getItems() {
        return this.items != null ? this.items : this._items;
    }

    public void updItems(List<T> items) {
        if (items != null) {
            addItems = new ArrayList<T>();
            updItems = new ArrayList<T>();
            delItems = new ArrayList<T>();

            List<Serializable> updIds = new ArrayList<Serializable>();
            for (T item : items) {
                if (item.getId() == null) {
                    addItems.add(item);

                } else {

                    for (AbstractDO _item : items) {
                        if (item.getId().equals(_item.getId())) {
                            updItems.add(item);
                            updIds.add(_item.getId());
                            break;
                        }
                    }
                }
            }

            for (T _item : _items) {
                if (!updIds.contains(_item.getId())) {
                    delItems.add(_item);
                }
            }
            this.items = items;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sync(BaseMapper mapper) {
        if (addItems != null) {
            for (AbstractDO item : addItems) {
                mapper.insert(item);
            }
        }

        if (updItems != null) {
            for (AbstractDO item : updItems) {
                mapper.updateSelective(item);
            }
        }

        if (delItems != null) {
            for (AbstractDO item : delItems) {
                mapper.delete(item.getId());
            }
        }
    }
}
