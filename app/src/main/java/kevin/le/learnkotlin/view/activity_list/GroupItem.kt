package kevin.le.learnkotlin.view.activity_list

import kevin.le.learnkotlin.model.ActivityListItemType

class GroupItem(var name: String) : ActivityListItem() {
    override fun getType(): Int {
        return ActivityListItemType.GROUP.ordinal
    }
}