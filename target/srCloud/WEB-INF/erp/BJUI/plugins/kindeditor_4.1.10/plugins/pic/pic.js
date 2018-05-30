/**
 * Created by DavidWang on 17/4/5.
 */
KindEditor.plugin('pic', function(K) {
    var self = this, name = 'pic';
    self.clickToolbar(name, function() {
        var path = self.pluginsPath + "pic/images/gou.png";
        self.insertHtml("<img src='"+path+"' />");
    });
});