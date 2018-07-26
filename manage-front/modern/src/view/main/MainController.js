Ext.define('Admin.view.main.MainController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main',

    slidOutCls: 'main-nav-slid-out',

    showNavigation: false,

    listen : {
        controller : {
            '#' : {
                unmatchedroute : 'onRouteChange'
            }
        }
    },

    routes: {
        ':node': 'onRouteChange'
    },

    config: {
        showNavigation: true
    },

    collapsedCls: 'main-nav-collapsed',

    init: function (view) {
        var me = this,
            refs = me.getReferences(),
            logo = refs.logo,
            nav;


        me.callParent([ view ]);

        me.nav = refs.navigation;
        me.navigationTree = refs.navigationTree;

        nav = me.nav;

        // Detach the navigation container so we can float it in from the edge.
        nav.getParent().remove(nav, false);
        nav.addCls(['x-floating', 'main-nav-floated', me.slidOutCls]);
        nav.setScrollable(true);
        nav.getRefOwner = function () {
            // we still need events to route here or our base
            return view;
        };

        // Also, transplant the logo from the toolbar to be docked at the top of the
        // floating nav.
        nav.add(logo);
        logo.setDocked('top');

        Ext.getBody().appendChild(nav.element);
    },


    onNavigationItemClick: function (tree, info) {
        if (info.select) {
            // If we click a selectable node, slide out the navigation tree. We cannot
            // use select event for this since the user may tap the currently selected
            // node. We don't want to slide out, however, if the tap is on an unselectable
            // thing (such as a parent node).
            this.setShowNavigation(false);
        }
    },

    onNavigationTreeSelectionChange: function (tree, node) {
        this.setShowNavigation(false);
        var to = node && (node.get('routeId') || node.get('viewType'));

        if (to) {
            this.redirectTo(to);
        }
    },

    onRouteChange: function (id) {
        this.setCurrentView(id);
    },

    onToggleNavigationSize: function () {
        this.setShowNavigation(!this.getShowNavigation());
    },

    setCurrentView: function (hashTag) {
        hashTag = (hashTag || '').toLowerCase();

        var me = this,
            refs = me.getReferences(),
            mainCard = refs.mainCard,
            navigationTree = me.navigationTree,
            store = navigationTree.getStore(),
            node = store.findNode('routeId', hashTag) ||
                   store.findNode('viewType', hashTag),
            item = mainCard.child('component[routeId=' + hashTag + ']');

        if (!item) {
            item = mainCard.add({
                xtype: node.get('viewType'),
                routeId: hashTag
            });
        }

        mainCard.setActiveItem(item);

        navigationTree.setSelection(node);

        //if (newView.isFocusable(true)) {
        //    newView.focus();
        //}
    },

    updateShowNavigation: function (showNavigation, oldValue) {
        // Ignore the first update since our initial state is managed specially. This
        // logic depends on view state that must be fully setup before we can toggle
        // things.
        //
        // NOTE: We do not callParent here; we replace its logic since we took over
        // the navigation container.
        //
        if (oldValue !== undefined) {
            var me = this,
                nav = me.nav,
                mask = me.mask;

            if (showNavigation) {
                me.mask = mask = Ext.Viewport.add({
                    xtype: 'loadmask',
                    userCls: 'main-nav-mask'
                });

                mask.element.on({
                    tap: me.onToggleNavigationSize,
                    scope: me,
                    single: true
                });
            } else if (mask) {
                mask.destroy();
                me.mask = null;
            }

            nav.toggleCls(me.slidOutCls, !showNavigation);
        }
    },

    toolbarButtonClick: function(btn){
        var href = btn.config.href;
        this.redirectTo(href);
    }
});
