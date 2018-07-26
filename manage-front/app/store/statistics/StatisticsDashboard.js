/**
 * Created by CC on 2017/3/21.
 */
Ext.define('Admin.store.statistics.StatisticsDashboard', {
    extend: 'Ext.data.Store',
    storeId: 'statistics.statisticsDashboard',

    //                   IE    Firefox  Chrome   Safari
    fields: ['datetime','dayname', 'totalOrder', 'totalPrice', 'totalPoid'],
    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Statistics', 'dashboard'),
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list'
        }
    }

});