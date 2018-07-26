Ext.define('Admin.view.authentication.AuthenticationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.authentication',

    onLoginButton: function(btn) {
        var me = this;
        var form = btn.up('form');
        if(form.isValid()) {
            me.goLogin(form,me);
        }
    },
    goLogin:function(form,me){
        form.submit({
            url: Common.Config.requestPath('Security','Authentic', 'login'),
            waitMsg: '请求中...',
            success: function (form, action) {
                var result = action.result;
                if (result.code != Common.Config.success_code) {
                    Ext.Msg.alert('温馨提示', result.message);
                    return false;
                }
                // 成功登陆
                Common.Config.storage.setItem(Common.Config.LOGINFLAG, 'true');
                var viewport = me.view.up('viewport');
                viewport.destroy();
                Ext.create('Ext.container.Viewport', {
                    items: {
                        xtype: 'main'
                    }
                });
                Ext.util.History.setHash('admindashboard');
            }
        });
    }

});