/**
 * Created by CC on 2016/9/2.
 */
Ext.define('Admin.store.dictionary.City', {
    extend: 'Admin.store.Base',

    storeId: 'dictionary.City',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Dictionary' , 'City')
        },
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});