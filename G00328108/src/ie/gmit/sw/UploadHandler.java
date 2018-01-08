package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ie.gmit.sw.Util.Config;

@WebServlet(asyncSupported = true, description = "Handles new document file upload", urlPatterns = { "/upload" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB. The file size in bytes after which the file will be
		// temporarily stored on disk. The default size is 0 bytes.
		maxFileSize = 1024 * 1024 * 10, // 50MB. The maximum size allowed for uploaded files, in bytes
		maxRequestSize = 1024 * 1024 * 50) // 51MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class UploadHandler extends HttpServlet {
	private static final long serialVersionUID = 465419841991L;

	public void init(ServletConfig config) throws ServletException {
		Config.setLoggingOn(Boolean.parseBoolean(config.getInitParameter("logging")));
		Config.setLogFile(config.getInitParameter("logFile"));
		Config.setDb(config.getInitParameter("dbFile"), config.getInitParameter("password"));
		Config.setHashFunctions(Integer.parseInt(config.getInitParameter("HashFunctionCount")));
		Config.setShingleSize(Integer.parseInt(config.getInitParameter("shingleSize")));
		Config.setRefreshRate(Integer.parseInt(config.getInitParameter("refreshRate")));
		Config.setNumOfWorkers(Integer.parseInt(config.getInitParameter("workers")));
		Util.init();
	}

	// Wrong request error handling.

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "First submit the file.");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// Process POST request initialized by 'index.jsp'

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int jobNumber = Util.getJobNumber();
			String title = request.getParameter("txtTitle");
			Part part = request.getPart("txtDocument");
			BufferedReader document = new BufferedReader(new InputStreamReader(part.getInputStream()));
			Job job = new Job(jobNumber, title, document);
			Util.processJob(job);
			response.sendRedirect("poll?title=" + title + "&jobNumber=" + jobNumber);
		} catch (Exception e) {
			System.out.println("[ERROR] UploadHadler: " + e.getMessage());
			response.sendRedirect("error.jsp");
		}
	}

	public void destroy() {
		Util.shutdown();
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
}
