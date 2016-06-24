import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private Worker[] workers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    static class Worker {
        public int id;
        public int jobId;
        public int nextFreeTime;

        public Worker(int id, int nextFreeTime, int jobId) {
            this.id = id;
            this.nextFreeTime = nextFreeTime;
            this.jobId = jobId;
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

    private int leftChild(int i) {
        return (2 * i) + 1;
    }

    private int rightChild(int i) {
        return (2 * i) + 2;
    }

    private int parent(int i) {
        return (int)Math.floor(i - 1) / 2;
    }

    private void buildHeap() {
        for(int i = (int)Math.floor(numWorkers / 2); i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftUp(int i) {
        while(i > 1 && workers[parent(i)].nextFreeTime < workers[i].nextFreeTime) {
            Worker temp = workers[parent(i)];
            workers[i] = workers[parent(i)];
            workers[parent(i)] = temp;
        }
    }

    private void changePriority(int i, int freeTime) {
        int oldFreeTime = workers[i].nextFreeTime;

        workers[i].nextFreeTime = freeTime;

        if(freeTime > oldFreeTime) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    private void siftDown(int i) {
        int maxIndex = i, l, r;

        l = leftChild(i);

        if(l < numWorkers && workers[l].nextFreeTime < workers[maxIndex].nextFreeTime) {
            maxIndex = l;
        }

        r = rightChild(i);

        if(r < numWorkers && workers[r].nextFreeTime < workers[maxIndex].nextFreeTime) {
            maxIndex = r;
        }

        if(i != maxIndex) {
            Worker temp = workers[maxIndex];
            workers[maxIndex] = workers[i];
            workers[i] = temp;

            siftDown(maxIndex);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        workers = new Worker[numWorkers];
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];

        for (int j = 0; j < numWorkers; j++) {
            workers[j] = new Worker(j, jobs[j], j);
        }

        buildHeap();

        for (int i = 0; i < jobs.length; i++) {
            
//            int duration = jobs[i];
//            int bestWorker = 0;
//            for (int j = 0; j < numWorkers; ++j) {
//                if (nextFreeTime[j] < nextFreeTime[bestWorker])
//                    bestWorker = j;
//            }
//            assignedWorker[i] = bestWorker;
//            startTime[i] = nextFreeTime[bestWorker];
//            nextFreeTime[bestWorker] += duration;
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
