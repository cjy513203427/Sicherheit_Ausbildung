/**
 * Created by CC on 2017/3/21.
 */
Ext.define('Admin.store.statistics.MonthChart', {
    extend: 'Ext.data.Store',
    storeId: 'statistics.monthChart',

    fields: ['tyoe', 'proportion', 'total'],
    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Statistics', 'monthChart'),
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list'
        }
    }

});