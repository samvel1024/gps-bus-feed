# GpsBusFeed

GpsBusFeed is an Android library which enables GPS tracking functinality right out of the box. It is based on [Otto](https://github.com/square/otto) event bus, [Dagger2](https://google.github.io/dagger/) and GooglePlay Services. 

### How to use

Create a custom Application subclass and link it in AndroidManifest.xml or implement TrackerAwareApplication and extend the GpsBusFeedComponent if you are using Dagger2 for injection. 

    public class MyApplication extends TrackerApplication {
        public LocationTracker getTracker(){
            return super.getGpsBusFeedComponent().getTracker();
        }
    }
    
Wire the LocationTracker instance in your onCreate()

    this.tracker = ((MyApplication) this.getApplicationContext()).getTracker();
    
If you wish to update your view subscribe for new location change events (more details [here](http://square.github.io/otto/))

    @Override
    protected void onStop() {
        tracker.unsubscribe(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        tracker.subscribe(this);
        super.onStart();
    }
    
    @Subscribe
    public void onLocationUpdated(LocationChangedEvent locationEvent) {
        Toast.makeText(this, "Received location event", Toast.LENGTH_SHORT).show();
    }
    
Configure and start the GPS tracker


    public void onTurnOnTracker(View view) {
        if (!tracker.isTrackerRunning()) {
            tracker.startTracker(withContext(this)
                    .timingStrategy(withTiming(onEveryMillis(5000)).from(10, 20).to(23,0))
                    .requestFactory(new DefaultLocationRequestFactory())

                    .filter(new LocationAccuracyEventFilter(500))
                    .permanentListener(LocationEventListener.class));
            Toast.makeText(this, "Tracker started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tracker is already running", Toast.LENGTH_SHORT).show();
        }
    }
    
### Configuring the tracker

The most important field in ConfigBuilder class is the permanentListener field. These should be serializable classes with @Subscribe annotations on listener methods which can receieve single LocationChangedEvent argument and perform some actions like saving in SQLite database or Uploading to a web server.
