package kevin.le.learnkotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.model.ActivityDataModel
import kevin.le.learnkotlin.model.IntentFactory
import kevin.le.learnkotlin.view.activity_list.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = getAdapter()
    }

    private fun getAdapter(): ActivityListAdapter {
        return ActivityListAdapter(getActivityListData(), getOnClickItemListener())
    }
    
    private fun getOnClickItemListener(): OnClickItemListener {
        return object : OnClickItemListener {
            override fun onClick(name: String) {
                val intent = IntentFactory().make(this@MainActivity, name)
                if (intent != null) {
                    startActivity(intent)
                }
            }
        }
    }

    private fun getActivityListData(): MutableList<ActivityListItem> {
        val listData = mutableListOf<ActivityListItem>()
        listData.add(GroupItem(name = "Font"))
        listData.add(ChildItem(activityModel = ActivityDataModel(name = "SystemFont", type = "Feature", title = "System Font")))
        return listData
    }
}