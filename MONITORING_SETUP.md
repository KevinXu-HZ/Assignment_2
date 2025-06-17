# User Management System - Monitoring Setup

This guide will help you set up Prometheus and Grafana monitoring for your Spring Boot User Management System.

## 🎯 What You'll Get

- **Total User Count**: Real-time gauge showing current number of users
- **Age Distribution**: Bar chart and pie chart showing users by age ranges:
  - 0-18 years
  - 19-35 years
  - 36-50 years
  - 51-65 years
  - 65+ years
- **User Creation Rate**: Track how fast users are being added
- **Historical Trends**: See user growth over time

## 🚀 Quick Start

### Step 1: Rebuild Your Spring Boot Application
Since we added new dependencies, you need to rebuild:

```bash
./mvnw clean compile
```

### Step 2: Start Your Spring Boot Application
```bash
./mvnw spring-boot:run
```

### Step 3: Start Monitoring Stack
In a new terminal window:

```bash
docker-compose up -d
```

### Step 4: Access the Services

- **Application**: http://localhost:8080
- **Prometheus**: http://localhost:9090  
- **Grafana**: http://localhost:3000
  - Username: `admin`
  - Password: `admin`

## 📊 Dashboard Features

### 1. Total Users Over Time
- Line chart showing user growth
- Real-time updates as you add users

### 2. Current Total Users  
- Large gauge showing current user count
- Updates immediately when users are added

### 3. User Age Distribution (Bar Chart)
- Horizontal bar chart showing user counts in each age range
- Great for understanding your user demographics

### 4. Age Distribution (Pie Chart)
- Visual percentage breakdown of age groups
- Easy to see which age group dominates

### 5. User Creation Rate
- Shows how quickly users are being added
- Useful for monitoring activity levels

## 🧪 Testing the Setup

1. **Add some test users** through the web interface at http://localhost:8080
   - Try users of different ages to see the age distribution charts
   - Add users like:
     - John, 25 years old
     - Sarah, 45 years old  
     - Bob, 70 years old
     - Alice, 16 years old

2. **Check Prometheus metrics** at http://localhost:9090/targets
   - Make sure your Spring Boot app target is "UP"

3. **View Grafana dashboard** at http://localhost:3000
   - Login with admin/admin
   - The dashboard should be automatically loaded
   - You should see your user data visualized

## 📈 Available Metrics

Your application exposes these custom metrics:

- `users_total` - Total number of users in database
- `users_created_total` - Counter of users created (cumulative)
- `users_age_range_0_to_18` - Count of users aged 0-18
- `users_age_range_19_to_35` - Count of users aged 19-35  
- `users_age_range_36_to_50` - Count of users aged 36-50
- `users_age_range_51_to_65` - Count of users aged 51-65
- `users_age_range_over_65` - Count of users over 65

## 🔧 Customization

### Adding New Age Ranges
Edit `src/main/kotlin/com/example/demo/service/MetricsService.kt` to modify age ranges or add new ones.

### Custom Dashboard
- Login to Grafana at http://localhost:3000
- Edit the existing dashboard or create new ones
- Add panels for additional metrics you want to track

### Prometheus Configuration
Edit `monitoring/prometheus/prometheus.yml` to:
- Change scrape intervals
- Add additional targets
- Configure alerting rules

## 🛑 Stopping Services

```bash
# Stop monitoring stack
docker-compose down

# Stop Spring Boot app
# Press Ctrl+C in the terminal where it's running
```

## 🔍 Troubleshooting

### Spring Boot App Not Showing in Prometheus
- Check that your app is running on port 8080
- Verify actuator endpoints: http://localhost:8080/actuator/prometheus
- Check Docker can reach host: `host.docker.internal:8080`

### No Data in Grafana
- Ensure Prometheus is scraping data (check targets page)
- Verify dashboard is using correct metric names
- Check time range in Grafana (default is last 1 hour)

### Docker Issues
- Make sure Docker is running
- Check container logs: `docker-compose logs prometheus grafana`
- Restart containers: `docker-compose restart`

## 🎉 Success!

Once everything is running, you should see:
- ✅ Spring Boot app at http://localhost:8080
- ✅ Prometheus collecting metrics at http://localhost:9090
- ✅ Grafana dashboards at http://localhost:3000
- ✅ Real-time visualization of your user data with age distributions!

Try adding users of different ages and watch the charts update in real-time! 