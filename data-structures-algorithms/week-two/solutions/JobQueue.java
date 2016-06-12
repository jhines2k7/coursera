import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;
    private Worker[] workers;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public class Worker {
        private int workerId;
        private int jobId;
        private int jobDuration;

        public Worker(int workerId, int jobId, int duration) {
            this.workerId = workerId;
            this.jobId = jobId;
            this.jobDuration = duration;
        }

        public int getWorkerId() {
            return workerId;
        }

        public void setWorkerId(int workerId) {
            this.workerId = workerId;
        }

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public int getJobDuration() {
            return jobDuration;
        }

        public void setJobDuration(int jobDuration) {
            this.jobDuration = jobDuration;
        }
    }

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void buildHeap() {
        for (int i = (int)Math.floor(workers.length / 2); i >= 0; i--) {
            siftDown(i);
        }
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void changePriority(int i, Worker worker) {

    }

    private void siftUp(int i) {

    }

    private void siftDown(int i) {
        int maxIndex = i, l, r;

        l = leftChild(i);

        if(l < workers.length && workers[l].jobDuration < workers[maxIndex].jobDuration) {
            maxIndex = l;
        }

        r = rightChild(i);

        if(r < workers.length && workers[r].jobDuration < workers[maxIndex].jobDuration) {
            maxIndex = r;
        }

        if(i != maxIndex) {
            Worker temp = workers[i];
            workers[i] = workers[maxIndex];
            workers[maxIndex] = temp;

            siftDown(maxIndex);
        }
    }

    private void assignJobs() {
       // Boolean finishedProcessingJobs = false, freeWorkers = false;

	    Worker[] workers = new Worker[numWorkers];

        for(int i = 0; i < numWorkers; i++) {
            workers[i] = new Worker(i, i, jobs[i]);
            assignedWorker[i] = workers[i].workerId;
            startTime[i] = 0;
        }

        buildHeap();

        int elapsedTime = 0, processedJobs = numWorkers;

        while(true){
            while(true) {
                if(workers[0].jobDuration == elapsedTime) {
                    processedJobs++;
                    workers[0].jobDuration = jobs[processedJobs];
                    workers[0].jobId = processedJobs;

                    assignedWorker[processedJobs] = workers[0].workerId;
                    startTime[processedJobs] = elapsedTime;
                } else {
                    break;
                }
            }

            elapsedTime++;

            if(processedJobs == jobs.length) {
                break;
            }
	    }
        // while !finishedProcessingJobs
        // assign jobs to free workers
        // build heap
        ////  if worker[0].jobRuntime == elapsedTime
        ////    remove worker[0]
        ////    freeWorkers++
        ////  else
        ////    break
        ////  elapsedTime++

        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
